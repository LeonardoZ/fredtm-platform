package com.fredtm.core.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Atividade extends Entidade{

	private static final long serialVersionUID = 1L;
	private String titulo;
	private String descricao;
	private TipoAtividade tipoAtividade;
	private Boolean ehQuantitativa;
	private String nomeItem;
	private Operacao operacao;

	public Atividade() {

	}

	public Atividade(String titulo, TipoAtividade tipoAtividade) {
		super();
		this.titulo = titulo;
		this.tipoAtividade = tipoAtividade;
	}

	public Atividade(String titulo, String descricao,
			TipoAtividade tipoAtividade) {
		super();
		this.titulo = titulo;
		this.descricao = descricao;
		this.tipoAtividade = tipoAtividade;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoAtividade getTipoAtividade() {
		return tipoAtividade;
	}

	public void setTipoAtividade(TipoAtividade tipoAtividade) {
		this.tipoAtividade = tipoAtividade;
	}

	public void setTipoAtividade(int tipoAtividade) {
		this.tipoAtividade = TipoAtividade.getByIdentificacao(tipoAtividade)
				.orElse(TipoAtividade.PRODUTIVA);
	}

	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

	public Boolean getEhQuantitativa() {
		return ehQuantitativa == null ? false : ehQuantitativa;
	}

	public void setEhQuantitativa(int ehQuantitativa) {
		this.ehQuantitativa = !(ehQuantitativa == 0);
	}

	public void setEhQuantitativa(Boolean ehQuantitativa) {
		this.ehQuantitativa = ehQuantitativa;
	}

	public String getNomeItem() {
		return nomeItem;
	}

	public void setNomeItem(String nomeItem) {
		this.nomeItem = nomeItem;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Atividade atividade = (Atividade) o;
		return new EqualsBuilder().append(operacao, atividade.operacao)
				.append(tipoAtividade, atividade.tipoAtividade)
				.append(titulo, atividade.titulo).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(operacao).append(tipoAtividade)
				.append(titulo).build();
	}

	@Override
	public String toString() {
		return "Atividade{" + "titulo='" + titulo + '\'' + ", descricao='"
				+ descricao + '\'' + ", tipoAtividade=" + tipoAtividade
				+ ", ehQuantitativa=" + ehQuantitativa + ", nomeItem='"
				+ nomeItem + '\'' + ", operacao=" + operacao + '}';

	}

	public void copy(Atividade param) {
		setId(param.getId());
		setTitulo(param.getTitulo());
		setDescricao(param.getDescricao());
		setEhQuantitativa(param.getEhQuantitativa());
		setNomeItem(param.getNomeItem());
		setTipoAtividade(param.getTipoAtividade());
		setOperacao(param.getOperacao());
	}
}
