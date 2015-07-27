package com.fredtm.api.resource;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fredtm.core.model.Operation;
import com.fredtm.resources.ActivityResource;
import com.fredtm.resources.CollectResource;
import com.fredtm.resources.OperationResource;
import com.fredtm.resources.base.ElementParser;

@Component
public class OperationResourceAssembler extends ElementParser<Operation, OperationResource> {


	@Autowired
	private ActivityResourceAssembler ars;

	@Autowired
	private CollectResourceAssembler cra;

	@Autowired
	private SyncResourceAssembler sra;

	@Override
	public OperationResource toResource(Operation entity) {

		return new OperationResource()
				.uuid(entity.getId())
				.name(entity.getName())
				.technicalCharacteristics(entity.getTechnicalCharacteristics())
				.company(entity.getCompany())
				.modification(entity.getModified())
				.accountId(entity.getAccount().getId())
				.activities(
						new HashSet<ActivityResource>(ars.toResources(entity
								.getActivities())))
				.collects(
						new HashSet<CollectResource>(cra.toResources(entity
								.getCollects())))
				.lastSync(sra.toResource(entity
						.getLastSync()));

	}

	@Override
	public Operation toEntity(OperationResource r) {
		return null;
	}


}
