package com.fredtm.api.resource;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fredtm.api.rest.OperationResources;
import com.fredtm.core.model.Collect;
import com.fredtm.core.model.Operation;
import com.fredtm.data.repository.AccountRepository;
import com.fredtm.data.repository.ActivityRepository;
import com.fredtm.data.repository.CollectRepository;
import com.fredtm.data.repository.OperationRepository;
import com.fredtm.data.repository.TimeActivityRepository;

@Component
public class OperationResourceAssembler extends
		JaxRsResourceAssemblerSupport<Operation, OperationResource> {

	@Autowired
	private TimeActivityRepository ta;
	@Autowired
	private OperationRepository repo;
	@Autowired
	private ActivityRepository actRepo;
	@Autowired
	private AccountRepository accRepo;
	
	@Autowired
	private ActivityResourceAssembler ars;
	@Autowired
	private CollectResourceAssembler cra;
	@Autowired
	private SyncResourceAssembler sra;

	public OperationResourceAssembler() {
		super(OperationResources.class, OperationResource.class);
	}

	@Override
	public OperationResource toResource(Operation entity) {

		return new OperationResource()
				.uuid(entity.getId())
				.name(entity.getName())
				.technicalCharacteristics(entity.getTechnicalCharacteristics())
				.company(entity.getCompany())
				.modification(entity.getModified())
				.activities(
						new HashSet<ActivityResource>(ars.toResources(entity
								.getActivities())))
				.collects(
						new HashSet<CollectResource>(cra.toResources(entity
								.getCollects())))
				.syncs(new HashSet<SyncResource>(sra.toResources(entity
						.getSyncs())));

	}

	@Autowired
	private CollectRepository collectRepo;
	@Autowired
	private TimeActivityResourceAssembler tara;

	@Override
	public Optional<Operation> fromResource(OperationResource d) {
		Operation op = d.getId() != null ? repo.findOne(d.getUuid())
				: new Operation();

		op.setTechnicalCharacteristics(d.getTechnicalCharacteristics());
		op.setName(d.getName());
		op.setCompany(d.getCompany());
		op.setModified(d.getModification());
		op.setAccount(accRepo.findOne(d.getAccountId()));
		op.setActivities(ars.fromResources(d.getActivities()));
		op.setSyncs(sra.fromResources(d.getSyncs()));

		repo.saveAndFlush(op);
		List<ActivityResource> resources = ars.toResources(op.getActivities());

		d.getCollects().forEach(c -> {
			c.setOperationId(op.getId());
			c.setActivitiesList(resources);
			Collect collect = cra.fromResource(c).get();
			collect.setOperation(op);
			op.addCollect(collect);
		});

		collectRepo.save(op.getCollects());
		repo.save(op);
		return Optional.of(op);
	}

}
