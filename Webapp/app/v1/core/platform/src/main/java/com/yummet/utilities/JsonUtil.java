package com.yummet.utilities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.joda.time.DateTime;
import org.json.JSONObject;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yummet.enums.PostVisibilityEnum;

/**
 * The Util class to convert between json string and the object
 * 
 * @author jassica
 * @since 1
 */
public class JsonUtil {
	/**
	 * Convert the incoming json data to a jsonBuilder understandable string
	 * @param incomingData
	 * @return
	 */
	public StringBuilder convertJsonStringToStringBuilder(
			InputStream incomingData) {
		StringBuilder inputStr = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				inputStr.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + inputStr.toString());
		return inputStr;
	}

	/**
	 * Get the standard gson for all platform objects
	 * @return
	 */
	public Gson getStandardGsonForPlatformObject() {
		GsonBuilder builder = new GsonBuilder();
//		Gson gson = builder.create();
		Gson gson = Converters.registerDateTime(builder).create();
//		SomeContainerObject original = new SomeContainerObject(new DateTime());
//		String json = gson.toJson(original);
		
//		JSONObject jsonObj = new JSONObject(inputStr.toString());
//		// we will ignore the newpassword field for platform User
//		UserObject userInfo = (UserObject) jsonHelper.json2Object(jsonObj,
//				UserObject.class);
		return gson;
	}
	/**
	 * Convert a JsonObject to a platform object
	 * 
	 * @param incomingData
	 * @return
	 */
	public Object json2Object(JSONObject json, Class pojo) throws Exception {
		// Use default constructor to create a new instance
		Object obj = pojo.newInstance();
		// Get all the parent declared fields as well
		for (; pojo != Object.class; pojo = pojo.getSuperclass()) {
			// Get all the fields in the class
			Field[] fields = pojo.getDeclaredFields();
			for (Field field : fields) {
				// set the field accessibility
				field.setAccessible(true);
				// get the field name
				String name = field.getName();
				// If the field is not passed in the json object, we need ignore
				// it
				try {
					json.get(name);
				} catch (Exception ex) {
					continue;
				}
				if (json.get(name) != null && !"".equals(json.getString(name))) {
					if (field.getType().equals(Long.class)
							|| field.getType().equals(long.class)) {
						// setter(obj, field.getName(),
						// Long.parseLong(json.getString(name)),
						// field.getType());
						field.set(obj, Long.parseLong(json.getString(name)));
					} else if (field.getType().equals(String.class)) {
						setter(obj, field.getName(), json.getString(name),
								field.getType());
						// field.set(obj, json.getString(name));
					} else if (field.getType().equals(Double.class)
							|| field.getType().equals(double.class)) {
						field.set(obj, Double.parseDouble(json.getString(name)));
					} else if (field.getType().equals(Integer.class)
							|| field.getType().equals(int.class)) {
						field.set(obj, Integer.parseInt(json.getString(name)));
					} else if (field.getType().equals(DateTime.class)) {
						field.set(obj, DateTime.parse(json.getString(name)));
					} else if (field.getType().equals(PostVisibilityEnum.class)) {
						field.set(obj, PostVisibilityEnum.fromDbValue(json.getString(name)));
					} else {
						continue;
					}
				}
			}
		}
		return obj;
	}

	public void setter(Object obj, String att, Object value, Class<?> type) {
		try {
			String first = att.substring(0, 1);
			att = att.replaceFirst(first, first.toUpperCase());
			Method method = obj.getClass().getMethod("set" + att, type);
			method.invoke(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
