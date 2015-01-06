package com.fredtm.core.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Operacao extends Entidade {
	
	private static final long serialVersionUID = 1L;
	private String nome;
	private String empresa;
	private String caracteristicasTecnicas;
	private List<Atividade> atividades;
	private List<Coleta> coletas;

	public Operacao(String nome, String empresa, String caracteristicasTecnicas) {
		super();
		this.nome = nome;
		this.empresa = empresa;
		this.caracteristicasTecnicas = caracteristicasTecnicas;
	}

	public Operacao() {
		atividades = new ArrayList<Atividade>();
		coletas = new ArrayList<Coleta>();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getCaracteristicasTecnicas() {
		return caracteristicasTecnicas;
	}

	public void setCaracteristicasTecnicas(String caracteristicasTecnicas) {
		this.caracteristicasTecnicas = caracteristicasTecnicas;
	}

	public List<Coleta> getColetas() {
		return coletas;
	}

	public void setColetas(List<Coleta> coletas) {
		this.coletas = coletas;
	}

	public void addColeta(Coleta c) {
		if (!coletas.contains(c)) {
			this.coletas.add(c);
		}
	}

	public void removeColeta(Coleta c) {
		this.coletas.remove(c);
	}

	public List<Atividade> getAtividadesPreDefinidas() {
		return atividades;
	}

	public void setAtividadesParciais(List<Atividade> atividadesParciais) {
		this.atividades = atividadesParciais;
	}

	public void addAtividade(Atividade atividade) {
		if (!atividades.contains(atividade)) {
			this.atividades.add(atividade);
		}
	}

	public void removeAtividade(Atividade atividade) {
		this.atividades.remove(atividade);
	}

	public boolean jaPossuiQuantitativa() {
		for (Atividade a : atividades) {
			if (a.getTipoAtividade().equals(TipoAtividade.PRODUTIVA)
					&& a.getEhQuantitativa()) {
				return true;
			}
		}
		return false;
	}

	public Atividade getAtividadeQuantitativa() {
		for (Atividade a : atividades) {
			if (a.getTipoAtividade().equals(TipoAtividade.PRODUTIVA)
					&& a.getEhQuantitativa()) {
				return a;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return nome + " - " + empresa;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(nome).append(empresa)
				.append(caracteristicasTecnicas).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operacao other = (Operacao) obj;
		return new EqualsBuilder().append(nome, other.nome)
				.append(empresa, other.empresa)
				.append(caracteristicasTecnicas, other.caracteristicasTecnicas)
				.isEquals();
	}
}
