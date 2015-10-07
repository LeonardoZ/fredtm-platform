package com.fredtm.api.resource;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fredtm.core.model.Operation;
import com.fredtm.data.repository.AccountRepository;
import com.fredtm.data.repository.OperationRepository;
import com.fredtm.resources.ActivityDTO;
import com.fredtm.resources.OperationDTO;
import com.fredtm.resources.base.ElementParser;

@Component
public class OperationResourceAssembler extends ElementParser<Operation, OperationDTO> {

	@Autowired
	private ActivityResourceAssembler ars;

	@Autowired
	private CollectResourceAssembler cra;

	@Autowired
	private SyncResourceAssembler sra;

	@Autowired
	private OperationRepository operationRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public OperationDTO toResource(Operation entity) {

		return new OperationDTO().uuid(entity.getUuid()).name(entity.getName())
				.technicalCharacteristics(entity.getTechnicalCharacteristics()).company(entity.getCompany())
				.modification(entity.getModified()).accountId(entity.getAccount().getUuid())
				.activities(new HashSet<ActivityDTO>(ars.toResources(entity.getActivities())))
				.collects(cra.toResources(entity.getCollectsList()))
				.lastSync(sra.toResource(entity.getLastSync()));

	}

	@Override
	public Operation fromResource(OperationDTO operationResource) {
		Operation operation = operationRepository.findByUuid(operationResource.getUuid());
		if (operation == null)
			operation = new Operation();

		operation.setUuid(operationResource.getUuid() == null || operationResource.getUuid().equals("") ? ""
				: operationResource.getUuid());
		operation.setTechnicalCharacteristics(operationResource.getTechnicalCharacteristics());
		operation.setName(operationResource.getName());
		operation.setCompany(operationResource.getCompany());
		operation.setModified(operationResource.getModification());
		operation.setAccount(accountRepository.findByUuid(operationResource.getAccountId()));
		return operation;
	}

}
