package com.froso.ufp.modules.optional.ftp.configuration;

import com.froso.ufp.core.exceptions.*;
import java.io.*;
import org.apache.commons.net.ftp.*;
import org.springframework.integration.ftp.session.*;

/**
 * Created by ckleinhuix on 28.07.2015.
 */
/*
 * Copyright 2002-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Default implementation of FTP SessionFactory.
 *
 * @author Iwein Fuld
 * @author Josh Long
 * @since 2.0
 */
public class FTPSessionFactoryMy extends AbstractFtpSessionFactory<FTPClient> {
    private String workingDirectory = "";

    /**
     * Instantiates a new Ftp session factory my.
     *
     * @param workPath the work path
     */
    public FTPSessionFactoryMy(String workPath) {

        if (workPath.trim().startsWith("/")) {
            // strip leading "/" if present!
            workPath = workPath.trim().substring(1);
        }

        workingDirectory = workPath.trim();

    }


    @Override
    protected FTPClient createClientInstance() {
        FTPClient result = new FTPClient();

        return result;
    }

    @Override
    public FtpSession getSession() {
        FtpSession result = null;
        try {
            result = super.getSession();
        } catch (Exception e) {
            throw new UFPRuntimeException("failed to create FTPClient", e);
        }


        //result.mkdir("test");

       /*
         */
        try {
            // Change working directory and create pathes if not exist
            if (!result.exists(workingDirectory)) {

                result.mkdir(workingDirectory);


            }
            result.getClientInstance().changeWorkingDirectory(workingDirectory);
        } catch (IOException e) {


            throw new UFPRuntimeException("failed to create FTPClient", e);

        }
        return result;

    }

}
