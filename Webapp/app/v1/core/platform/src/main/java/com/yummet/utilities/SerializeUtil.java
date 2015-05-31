package com.yummet.utilities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.yummet.entities.EntityObject;

/**
 * Serialize the entity object and deserialize
 * @author jassica
 *
 */
public class SerializeUtil {

	/**
	 * Serialize the whole object to the bytes
	 * @param object
	 * @return
	 */
	public static byte[] serialize(EntityObject object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	/**
	 * Convert the bytes back to EntityObject
	 * @param bytes
	 * @return
	 */
	public static EntityObject deserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (EntityObject)ois.readObject();
		} catch (Exception e) {
		}
		return null;

	}

}