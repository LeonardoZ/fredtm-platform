package com.fredtm.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fredtm.core.model.Sync;
import com.fredtm.data.repository.SyncRepository;
import com.fredtm.resources.SyncResource;
import com.fredtm.resources.base.ElementParser;

@Component
public class SyncResourceAssembler extends
		ElementParser<Sync, SyncResource> {

	@Autowired
	private SyncRepository repository;

	@Override
	public SyncResource toResource(Sync entity) {
		SyncResource sr = new SyncResource();
		sr.uuid(entity.getId()).created(entity.getCreated())
				.operationId(entity.getOperation().getId());
			
		return sr;

	}

	@Override
	public Sync toEntity(SyncResource r) {
		Sync s =  hasValidUuid(r)  ? repository.findOne(r.getUuid())
				: new Sync();
		s.setCreated(r.getCreated());
		return s;
	}
}
