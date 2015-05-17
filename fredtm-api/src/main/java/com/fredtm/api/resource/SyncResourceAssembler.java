package com.fredtm.api.resource;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fredtm.api.rest.SyncResources;
import com.fredtm.core.model.Sync;
import com.fredtm.data.repository.SyncRepository;

@Component
public class SyncResourceAssembler extends
		JaxRsResourceAssemblerSupport<Sync, SyncResource> {

	@Autowired
	private SyncRepository repository;

	public SyncResourceAssembler() {
		super(SyncResources.class, SyncResource.class);
	}

	@Override
	public SyncResource toResource(Sync entity) {
		SyncResource sr = new SyncResource();
		sr.uuid(entity.getId()).created(entity.getCreated())
				.operationId(entity.getOperation().getId())
				.jsonOldData(new String(entity.getJsonOldData()).intern());
		return sr;

	}

	@Override
	public Optional<Sync> fromResource(SyncResource d) {
		Sync s =  hasValidUuid(d)  ? repository.findOne(d.getUuid())
				: new Sync();
		s.setJsonOldData(d.getJsonOldData().getBytes());
		s.setCreated(d.getCreated());
		return Optional.of(s);
	}
}
