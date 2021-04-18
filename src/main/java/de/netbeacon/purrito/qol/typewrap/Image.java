/*
 *     Copyright 2021 Horstexplorer @ https://www.netbeacon.de
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.netbeacon.purrito.qol.typewrap;

import de.netbeacon.purrito.core.Purrito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Represents an image object when the data is held locally
 */
public class Image {

    private final byte[] bytes;
    private final Logger logger = LoggerFactory.getLogger(Purrito.class);

    /**
     * Creates a new instance of this class
     * @param bytes the image as bytes
     */
    public Image(byte[] bytes){
        this.bytes = bytes;
    }

    /**
     * Get the image as raw bytes
     * @return image bytes
     */
    public byte[] getBytes(){
        return bytes;
    }

    /**
     * Creates an InputStream from the bytes
     * @return input stream
     */
    public InputStream getAsInputStream(){
        return new ByteArrayInputStream(bytes);
    }

    /**
     * Creates a buffered image from the bytes
     * @return BufferedImage or null on exception
     */
    public BufferedImage getAsBufferedImage(){
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes)){
            return ImageIO.read(byteArrayInputStream);
        }catch (Exception e){
            logger.error("Failed to get image as BufferedImage", e);
        }
        return null;
    }

    /**
     * Writes the bytes to a file
     * @param file to save the bytes to
     * @return the path of the stored file or null on exception
     */
    public Path writeToFile(File file){
        try{
            return Files.write(file.toPath(), bytes);
        }catch (Exception e){
            logger.error("An exception occurred writing file image to file", e);
        }
        return null;
    }
}
