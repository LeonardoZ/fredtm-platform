package com.fredtm.resources;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class OperationsDTO {

	@JsonProperty("_embedded")
	@SerializedName("_embedded")
	private Embedded embedded;
	
	private Page page;

	public Page getPage() {
		return this.page;
	}
	
	public void setPage(Page page) {
		this.page = page;
	}
	
	public Embedded getEmbedded() {
		return embedded;
	}

	public void setEmbedded(Embedded embedded) {
		this.embedded = embedded;
	}

	 public class Page {
        int size, totalElements,totalPages,number;
        
        public int getNumber() {
			return this.number;
		}

		public int getSize() {
			return this.size;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public int getTotalElements() {
			return this.totalElements;
		}

		public void setTotalElements(int totalElements) {
			this.totalElements = totalElements;
		}

		public int getTotalPages() {
			return this.totalPages;
		}

		public void setTotalPages(int totalPages) {
			this.totalPages = totalPages;
		}

		public void setNumber(int number) {
			this.number = number;
		}
        
    }
	
	public class Embedded {

		List<OperationDTO> operationDTOList;

		public List<OperationDTO> getOperationDTOList() {
			return operationDTOList;
		}

		public void setOperationsDTOList(List<OperationDTO> operations) {
			this.operationDTOList = operations;
		}

	}

}
