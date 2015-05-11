package com.fredtm.api.dto;

import com.fredtm.api.rest.AccountResources;
import com.fredtm.core.model.TimeActivity;

public class TimeAcitivityResourceAssembler extends
		JaxRsResourceAssemblerSupport<TimeActivity, TimeActivityResource> {

	public TimeAcitivityResourceAssembler() {
		super(AccountResources.class, TimeActivityResource.class);
	}

	@Override
	public TimeActivityResource toResource(TimeActivity entity) {
		return null;
	}

}
