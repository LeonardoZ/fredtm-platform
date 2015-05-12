package com.fredtm.api.resource;

import com.fredtm.api.rest.OperationResources;
import com.fredtm.core.model.Operation;

public class OperationResourceAssembler extends
		JaxRsResourceAssemblerSupport<Operation, OperationResource> {

	public OperationResourceAssembler() {
		super(OperationResources.class, OperationResource.class);
	}

	@Override
	public OperationResource toResource(Operation entity) {
		ActivityResourceAssembler ars = new ActivityResourceAssembler();
		CollectResourceAssembler cra = new CollectResourceAssembler();
		SyncResourceAssembler sra = new SyncResourceAssembler();
		return new OperationResource().name(entity.getName())
				.technicalCharacteristics(entity.getTechnicalCharacteristics())
				.company(entity.getCompany())
				.modification(entity.getModified()).uuid(entity.getUuid())
				.activities(ars.toResources(entity.getActivities()))
				.collects(cra.toResources(entity.getCollects()))
				.syncs(sra.toResources(entity.getSyncs()));

	}

}
