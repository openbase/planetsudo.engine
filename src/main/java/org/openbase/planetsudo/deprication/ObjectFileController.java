/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.deprication;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2016 openbase.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class ObjectFileController<O> {

    private static final Logger logger = LoggerFactory.getLogger(ObjectFileController.class);
    
	private final String fileURI;

	public ObjectFileController(String fileURI) {
		this.fileURI = fileURI;
	}

	public boolean writeObject(O object) { // TODO: Serialize with json and support spar Data.
		logger.debug("write to " + fileURI);
		synchronized (this) {
			FileOutputStream fileOutputStream = null;

			try {
				fileOutputStream = new FileOutputStream(fileURI);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(object);
			} catch (IOException e) {
				logger.error("Could not write " + object + " in " + fileURI, e);
				return false;
			} finally {
				try {
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
				} catch (Exception e) {
					logger.warn("Could not close stream to " + fileURI, e);
					return false;
				}
			}
			return true;
		}
	}

	public O readObject() throws IOException, ClassNotFoundException, FileNotFoundException {
		logger.debug("Read " + fileURI);

		synchronized (this) {
			FileInputStream fileInputStream = null;
			O object = null;
			try {

				fileInputStream = new FileInputStream(fileURI);

				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
				object = (O) objectInputStream.readObject();
				logger.info("Read " + fileURI);
			} catch (FileNotFoundException ex) {
				throw new IOException("Could not found File[" + fileURI + "]", ex);
			} catch (ClassNotFoundException ex) {
				deleteDataFile();
				throw new IOException("File[" + fileURI + "] not compatible with progam version!", ex);
			} catch (InvalidClassException ex) {
				deleteDataFile();
				throw new IOException("File[" + fileURI + "] not compatible with progam version! Class struct changed!", ex);
			} catch (Exception ex) {
				throw new IOException("Could not read File[" + fileURI + "]", ex);
			} finally {
				try {
					if (fileInputStream != null) {
						fileInputStream.close();
					}
				} catch (Exception e) {
					throw new IOException("Could not close fileStream " + fileURI + " :" + e.getMessage());
				}
			}
			return object;
		}
	}

	private void deleteDataFile() {
		logger.warn("Initiate delete of " + fileURI);
		try {
			new File(fileURI).delete();
		} catch (Exception ex) {
			logger.warn("Could not delete " + fileURI, ex);
		}
	}
}
