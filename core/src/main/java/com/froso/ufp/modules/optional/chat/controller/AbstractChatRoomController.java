package com.froso.ufp.modules.optional.chat.controller;

import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.modules.core.user.service.*;
import com.froso.ufp.modules.optional.chat.model.*;
import com.froso.ufp.modules.optional.chat.service.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Created with IntelliJ IDEA. SimpleUser:Christian Kleinhuis (ck@froso.de) Date: 16.11.13 Time: 20:57 To change
 * this
 * template use File | Settings | File Templates.
 *
 * @param <T> the type parameter
 * @param <X> the type parameter
 */
public class AbstractChatRoomController<T extends ChatRoom, X extends ChatData> {


    /**
     * The Chat room service.
     */
    @Autowired
    protected ChatRoomService<T> chatRoomService;
    /**
     * The Chat data service.
     */
    @Autowired
    protected ChatDataService<X> chatDataService;
    @Autowired
    private ICoreUserService coreUserService;


    /**
     * Create chatroom chat room.
     *
     * @param topic      the topic
     * @param name       the name
     * @param coreUserId the core user id
     * @return the chat room
     */
    public T createChatroomWithCoreUser(List<DataDocumentLink<IDataDocument>> topic, String name, String coreUserId) {
        T result = createChatroom(topic, name);
        result.getMetaData().getCreatorUserLink().setId(coreUserId);
        chatRoomService.save(result);
        return result;


    }

    /**
     * Create chat message with core user chat room.
     *
     * @param roomId the room id
     * @param data   the data
     * @return the chat room
     */
    public X createChatMessageWithCoreUser(String roomId, ChatCreateData data) {
        X result = createChatMessage(roomId, data);


        //  chatDataService.save(result);
        return result;


    }

    /**
     * Create chat message chat data.
     *
     * @param roomId the room id
     * @param data   the data
     * @return the chat data
     */
    public X createChatMessage(String roomId, ChatCreateData data) {

        X chatRoom = chatDataService.createNewDefault();

        chatRoom.setMessage(data.getMessage());
        chatRoom.setTitle(data.getTitle());
        chatRoom.setSummary(data.getSummary());
        chatRoom.setChatRoomLink(new DataDocumentLink<ChatRoom>(roomId, "ChatRoom"));

        chatDataService.save(chatRoom);
        return chatRoom;


    }

    /**
     * Create chatroom chat room.
     *
     * @param topic the topic
     * @param name  the name
     * @return the chat room
     */
    public T createChatroom(List<DataDocumentLink<IDataDocument>> topic, String name) {


        T chatRoom = chatRoomService.createNewDefault();

        for (DataDocumentLink<IDataDocument> documentLink : topic) {
            chatRoom.getTopic().add(documentLink);

        }
        chatRoom.setName(name);

        chatRoomService.save(chatRoom);
        return chatRoom;


    }

    /**
     * Gets chat rooms for id.
     *
     * @param topic the topic
     * @return the chat rooms for id
     */
// fixme: move to service
    protected List<T> getChatRoomsForId(String topic) {

        //  return chatRoomService.findByKeyValue("topic._id", topic);
        List<DataDocumentLink<IDataDocument>> list = new ArrayList<>();
        list.add(new DataDocumentLink<IDataDocument>(topic));
        return getChatRoomsForId(list);
    }


    /**
     * Gets chat rooms for id.
     *
     * @param topic the topic
     * @return the chat rooms for id
     */
    protected List<T> getChatRoomsForId(List<DataDocumentLink<IDataDocument>> topic) {
        StringBuilder search = new StringBuilder();
        search.append('[');
        Iterator<DataDocumentLink<IDataDocument>> iter = topic.iterator();
        while (iter.hasNext()) {
            search.append(iter.next().getId());
            if (iter.hasNext()) {
                search.append(',');
            }
        }
        search.append(']');
        return chatRoomService.findByKeyValue("topic._id", search.toString());
    }


    /**
     * Create chat entry chat room.
     *
     * @param roomId     the room id
     * @param coreUserId the core user id
     * @param message    the message
     * @return the chat room
     */
    public T createChatEntry(String roomId, String coreUserId, String message) {


        T chatRoom = chatRoomService.findOne(roomId);


        X chatData = chatDataService.createNewDefault();
        chatData.getChatRoomLink().setId(roomId);
        chatData.getCoreUser().setId(coreUserId);
        chatData.setMessage(message);

        chatDataService.save(chatData);
        return chatRoom;


    }

    private void resolveUser(List<X> list) {

        for (X item : list) {

            if (item.getCoreUser() != null) {
                item.getCoreUser().setData(coreUserService.findOneCoreUser(item.getCoreUser().getId()));
            }

        }

    }

    /**
     * Gets entries for room.
     *
     * @param roomId the room id
     * @return the entries for room
     */
    public List<X> getEntriesForRoom(String roomId) {
        // verify that chat room exists
        chatRoomService.findOne(roomId);

        List<X> result = chatDataService.findByKeyValue("chatRoomLink._id", "=" + roomId);
        resolveUser(result);

        return result;


    }


}
