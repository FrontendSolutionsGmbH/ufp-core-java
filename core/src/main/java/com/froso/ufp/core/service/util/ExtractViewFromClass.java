package com.froso.ufp.core.service.util;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import com.froso.ufp.modules.core.resourcemetadata.model.*;
import org.slf4j.*;

import java.lang.reflect.*;
import java.util.*;

/**
 * utility class
 * <p>
 * - to extract ResourceMetadata annotations from ufp, to create default view-columns, view-filter
 * <p>
 * and some additional metadata like
 * <p>
 * - extract DataDocumentLink properties
 */
public class ExtractViewFromClass {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtractViewFromClass.class);

    public static final ResourceMetadata getMetaDataForResourceFromAnnotations(Class classIn) {

        ResourceMetadata result = new ResourceMetadata();

        if (classIn.isAnnotationPresent(UFPResourceMetadataAnnotation.class)) {
            UFPResourceMetadataAnnotation ta = (UFPResourceMetadataAnnotation) classIn.getAnnotation(UFPResourceMetadataAnnotation.class);
            result.setResourceIcon(ta.icon());

            ResourceViewsAnnotation views = ta.views();

            for (ResourceViewAnnotation viewAnnotation : views.value()) {

                result.getViews().add(resourceViewFromAnnotationAndClass(classIn, viewAnnotation));
            }

            ResourceKeywords keywords = ta.keywords();
            for (ResourceKeyword keyword : keywords.value()) {

                result.getTags().add(keyword.value());
            }

            for (ResourceView view : result.getViews()) {
// if tags empty in view, use tags from resource
                if (view.getTags().isEmpty()) {
                    view.setTags(result.getTags());
                }

                if (view.getSorting().isEmpty()) {
                    view.getSorting().add(SortData.getDefaultSortData());
                }

/*                for (String s : classIn.getName().split("\\.")) {
                    view.getTags().add(s.toUpperCase());
                }
                */
                //     view.getTags().add(classIn.getName().toUpperCase());

            }

            // default view
            result.setDefaultView(resourceViewFromAnnotationAndClass(classIn, ta.defaultView()));
            if (result.getDefaultView().getTags().isEmpty()) {
                result.getDefaultView().setTags(result.getTags());
            }

            if (result.getDefaultView().getSorting().isEmpty()) {
                result.getDefaultView().getSorting().add(SortData.getDefaultSortData());
            }

       /*     for (String s : classIn.getName().split("\\.")) {
                result.getDefaultView().getTags().add(s.toUpperCase());
            }
            result.getDefaultView().getTags().add(classIn.getName().toUpperCase());
*/
        }

        return result;

    }

    public static final List<String> getResourceTagsFrom(Class classIn) {
        List<String> result = new ArrayList<>();

        if (classIn.isAnnotationPresent(ResourceKeywords.class)) {

            ResourceKeywords ta = (ResourceKeywords) classIn.getAnnotation(ResourceKeywords.class);
            for (ResourceKeyword keyWord : ta.value()) {
                result.add(keyWord.value());
            }
        }
        return result;
    }

    public static final Set<String> getResourceTagsFrom(ResourceKeywords classIn) {
        Set<String> result = new HashSet<>();

        for (ResourceKeyword keyWord : classIn.value()) {
            result.add(keyWord.value());
        }
        return result;
    }

    public static final List<String> getDefaultVisibleColumns(Class classIn) {
        List<String> result = new ArrayList<>();

        if (classIn.isAnnotationPresent(DefaultVisibleColumns.class)) {

            DefaultVisibleColumns ta = (DefaultVisibleColumns) classIn.getAnnotation(DefaultVisibleColumns.class);
            for (ResourceVisibleColumn keyWord : ta.value()) {
                result.add(keyWord.value());
            }

        }

        return result;

    }

    public static Class getClassOfRepository(Type type) {
        if (type instanceof ParameterizedType) {

            ParameterizedType parameterizedType = (ParameterizedType) type;

            Type typeArgument = parameterizedType.getActualTypeArguments()[0];

            if (typeArgument instanceof Class) {

                return (Class) typeArgument;
            }
            //   return (Class) superclass.getActualTypeArguments()[0];
        }
        return null;
    }

    public static List<ForeignKey> inspect(Class klazz, Integer depth, String pathIn) {

        String path = pathIn;
        if (null == path) {
            path = "";
        }
        if (!"".equals(path)) {
            path = pathIn + '.';
        }

        if (depth > 10) {
            return new ArrayList<>();
        }
        List<ForeignKey> result = new ArrayList<>();
        if (klazz != null) {

            Field[] fields = klazz.getDeclaredFields();
            LOGGER.info(String.format("%d fields:%n", fields.length));
            for (Field field : fields) {
                //   if ( DataDocumentLink.class.in  field.getType() instanceof DataDocumentLink) {
                //       LOGGER.info("found link " + field.toString());
//            }
                LOGGER.info(String.format("%s %s %s%n",
                        Modifier.toString(field.getModifiers()),
                        field.getType().getSimpleName(),
                        field.getName()

                ));
                if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {

                    LOGGER.error("Field is static {} {}", klazz.getSimpleName(), field.getName());
                } else if (field.isAnnotationPresent(UfpPossibleLinkTypes.class)) {

                    UfpPossibleLinkTypes linktypes = field.getAnnotation(UfpPossibleLinkTypes.class);
                    for (int i = 0; i < linktypes.value().length; i++) {
                        ForeignKey foreignKey = new ForeignKey();
                        foreignKey.setFieldName(field.getName() + ".id");
                        foreignKey.setResourceName(linktypes.value()[i].getSimpleName());
                        result.add(foreignKey);
                    }

                } else if (field.getType().getSimpleName().contains(DataDocumentLink.class.getSimpleName())) {
                    try {

                        Class refClass = getClassOfRepository(field.getGenericType());
                     /*   if (ICoreUser.class.isAssignableFrom(refClass)) {


                            ForeignKey foreignKey = new ForeignKey();
                            foreignKey.setFieldName(field.getName() + ".id");
                            foreignKey.setResourceName(service.getTypeName());
                            result.add(foreignKey);

                        }else if (IClient.class.isAssignableFrom(refClass)) {


                            ForeignKey foreignKey = new ForeignKey();
                            foreignKey.setFieldName(field.getName() + ".id");
                            foreignKey.setResourceName(clientService.getTypeName());
                            result.add(foreignKey);

                        } else {
*/
                        LOGGER.info(String.format("Found LINK TYPE " + refClass.getName()));
//                        LOGGER.info(String.format("Found LINK TYPE " + getTypeNameOfResource(refClass)));

                        ForeignKey foreignKey = new ForeignKey();
                        foreignKey.setFieldName(path + field.getName() + ".id");
                        foreignKey.setResourceName(refClass.getSimpleName());
                        result.add(foreignKey);
                        //   }
                    } catch (Exception e) {
                        LOGGER.error("Problem with foreign key {} {}", klazz.getSimpleName(), field.getName(), e);
                    }
                } else {
                    LOGGER.error("Need to inspect further: {} {}", klazz.getSimpleName(), field.getName());
                    List<ForeignKey> subResult = inspect(field.getType(), depth + 1, path + field.getName());
                    LOGGER.info("SUB QUERY is {}", subResult);
                    result.addAll(subResult);
                    /*
                    if (field.getType().getName().contains("com.froso") && !field.getClass().isEnum()) {
                        List<ForeignKey> subtypes = inspect(field.getType(), depth + 1);
                        for (ForeignKey foreignKey : subtypes) {
                            ForeignKey newForeignKey = new ForeignKey();
                            newForeignKey.setFieldName(field.getName() + '.' + foreignKey.getFieldName());
                            newForeignKey.setResourceName(foreignKey.getResourceName());
                            result.add(newForeignKey);
                        }
                    }
                    */
                }
            }

            result.addAll(inspect(klazz.getSuperclass(), depth + 1, path));
        }
        return result;
    }

    public static List<ForeignKey> getForeignKeys(Class classIn) {
        List<ForeignKey> result = new ArrayList<>();
        result.addAll(inspect(classIn, 0, ""));
        return result;
    }

    public static String getTypeNameOfResource(Class classInh) {

        return "implement please";
    }

    private static ResourceView resourceViewFromAnnotationAndClass(Class classIn, ResourceViewAnnotation annotation) {
        ResourceView temp = resourceViewFromAnnotation(annotation);

        if (IDataDocumentWithName.class.isAssignableFrom(classIn)) {

            temp.getColumns().getFixed().add("name");
        } else {
            temp.getColumns().getFixed().add("id");

        }

        // make fixed column visible
        if (temp.getColumns().getVisible().indexOf(temp.getColumns().getFixed().get(0)) == -1) {

            temp.getColumns().getVisible().add("id");
        }

        return temp;
    }

    private static ResourceView resourceViewFromAnnotation(ResourceViewAnnotation annotation) {

        ResourceView result = new ResourceView();
        result.setName(annotation.name());
        result.setViewType(annotation.viewType());
        for (ResourceFilterKeyValue keyValue : annotation.filter()) {
            result.getFilter().put(keyValue.key(), keyValue.value());
        }

//        for (ResourceVisibleColumn visibleColumn : annotation.visibleColumns().value()) {
//            result.getColumns().getVisible().add(visibleColumn.value());
//        }

        result.setTags(getResourceTagsFrom(annotation.keywords()));

        Integer sortIndex = 0;
        for (ResourceFilterSortValue keyValue : annotation.sort().value()) {

            SortData sort = new SortData();
            sort.setDirection(keyValue.direction());
            sort.setName(keyValue.value());
            sort.setIndex(sortIndex);
            sortIndex++;
            result.getSorting().add(sort);
        }

        return result;

    }

}

