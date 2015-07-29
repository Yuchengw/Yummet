package com.yummet.api.rest;

/**
 * @author yucheng
 * @since 1
 * */
public class Formats {
	
	public static final Format Json = new FormatImpl("application/json", "json");

	public static class FormatImpl implements Format {
		
		private final String mediaType;
		private final String extension;
		
		public FormatImpl(String mediaType, String extension) {
			this.mediaType = mediaType;
			this.extension = extension;
		}
		
		public String getMediaType() {
			return this.mediaType;
		}
		
		public String  getExtension() {
			return this.extension;
		}
		
		
		public boolean equals(Object o) {
			if (o == null) {
				return false;
			}
			if (o.getClass() != getClass()) {
				return false;
			}
			return ((Format)o).getMediaType().equals(this.mediaType);
		}
		
		public int hashCode() {
			return this.mediaType.hashCode();
		}
	}
}
