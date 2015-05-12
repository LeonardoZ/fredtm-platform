package com.fredtm.api.resource;

import java.util.List;
import java.util.stream.Collectors;

import com.fredtm.api.rest.SyncResources;
import com.fredtm.core.model.Sync;

public class SyncResourceAssembler extends
		JaxRsResourceAssemblerSupport<Sync, SyncResource> {

	public SyncResourceAssembler() {
		super(SyncResources.class, SyncResource.class);
	}

	@Override
	public SyncResource toResource(Sync entity) {
		SyncResource sr = new SyncResource();
		List<Long> operations = entity.getOperations().stream().map(o -> o.getId())
				.collect(Collectors.toList());
		sr.pkId(entity.getId()).when(entity.getWhen())
				.accountId(entity.getAccount().getId())
				.jsonNewData(new String(entity.getJsonNewData()).intern())
				.jsonOldData(new String(entity.getJsonOldData()).intern())
				.operationsIds(operations);
		return null;

	}
}
