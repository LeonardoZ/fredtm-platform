package com.fredtm.resources;

public class PictureResource {

	private byte[] pictureContent;
	private String compressedPictureContent;
	private String url;
	
	public PictureResource() {
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getCompressedPictureContent() {
		return this.compressedPictureContent;
	}

	public void setCompressedPictureContent(String compressedPictureContent) {
		this.compressedPictureContent = compressedPictureContent;
	}




	public PictureResource(byte[] pictureContent) {
		super();
		this.pictureContent = pictureContent;
	}


	public byte[] getPictureContent() {
		return this.pictureContent;
	}

	public void setPictureContent(byte[] pictureContent) {
		this.pictureContent = pictureContent;
	}

}
