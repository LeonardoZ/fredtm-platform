package com.fredtm.api.resource;

import org.springframework.stereotype.Component;

import com.fredtm.core.model.Sync;
import com.fredtm.resources.SyncDTO;
import com.fredtm.resources.base.ElementParser;

@Component
public class SyncResourceAssembler extends
		ElementParser<Sync, SyncDTO> {

	@Override
	public SyncDTO toResource(Sync entity) {
		if(entity == null) return new SyncDTO();
		SyncDTO sr = new SyncDTO();
		sr.uuid(entity.getUuid())
			.created(entity.getCreated())
			.operationId(entity.getOperation().getUuid());
			
		return sr;

	}

	@Override
	public Sync fromResource(SyncDTO resource) {
		// TODO Auto-generated method stub
		return null;
	}

}
