package com.fredtm.api.resource;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.fredtm.api.rest.SyncResources;
import com.fredtm.core.model.Sync;
import com.fredtm.data.repository.SyncRepository;

public class SyncResourceAssembler extends
		JaxRsResourceAssemblerSupport<Sync, SyncResource> {

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

	@Autowired
	private SyncRepository repository;

	@Override
	public Optional<Sync> fromResource(SyncResource d) {
		Sync s = repository.findOne(d.getUuid());
		if (s == null) {
			return Optional.empty();
		}
		s.setJsonOldData(d.getJsonOldData().getBytes());
		s.setCreated(d.getCreated());
		return Optional.of(s);
	}
}
