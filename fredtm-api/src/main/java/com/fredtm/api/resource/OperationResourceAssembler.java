package com.fredtm.api.resource;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fredtm.api.rest.OperationResources;
import com.fredtm.core.model.Operation;
import com.fredtm.data.repository.OperationRepository;

@Component
public class OperationResourceAssembler extends
		JaxRsResourceAssemblerSupport<Operation, OperationResource> {
	
	@Autowired
	private OperationRepository repo;
	private ActivityResourceAssembler ars = new ActivityResourceAssembler();
	private CollectResourceAssembler cra = new CollectResourceAssembler();
	private SyncResourceAssembler sra = new SyncResourceAssembler();

	public OperationResourceAssembler() {
		super(OperationResources.class, OperationResource.class);
	}

	@Override
	public OperationResource toResource(Operation entity) {

		return new OperationResource().uuid(entity.getId())
				.name(entity.getName())
				.technicalCharacteristics(entity.getTechnicalCharacteristics())
				.company(entity.getCompany())
				.modification(entity.getModified())
				.activities(ars.toResources(entity.getActivities()))
				.collects(cra.toResources(entity.getCollects()))
				.syncs(sra.toResources(entity.getSyncs()));

	}

	@Override
	public Optional<Operation> fromResource(OperationResource d) {
		System.err.println(d);
		System.err.println(repo);
		Operation op = repo.findOne(d.getUuid());
		if (op == null) {
			return Optional.empty();
		}
		op.setTechnicalCharacteristics(d.getTechnicalCharacteristics());
		op.setName(d.getName());
		op.setCompany(d.getCompany());
		op.setModified(d.getModification());
		op.setActivities(ars.fromResources(d.getActivities()));
		op.setSyncs(sra.fromResources(d.getSyncs()));
		return Optional.of(op);
	}

}
