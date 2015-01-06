package com.fredtm.core.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fredtm.core.util.FormatadorTempoDecorrido;

public class TempoAtividade extends Entidade {
	private static final long serialVersionUID = 1L;
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss", new Locale("pt", "BR"));
	private Atividade atividade;
	private Coleta coleta;
	private Calendar dataManager = GregorianCalendar.getInstance();
	private long dataFim = 0l;
	private long dataInicio = 0l;
	private long tempoCronometrado = 0l;
	private int quantidadeColetada;

	public TempoAtividade() {
	}

	public TempoAtividade(Atividade atividadePai, Coleta cicloPai) {
		super();
		this.atividade = atividadePai;
		this.coleta = cicloPai;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public Long getTempoCronometrado() {
		return tempoCronometrado - (tempoCronometrado % 1000);
	}

	public void setTempoCronometrado(Long tempoCronometrado) {
		this.tempoCronometrado = tempoCronometrado;
	}

	public Long getDataFim() {
		return dataFim;
	}

	public void setDataFim(Long dataFim) {
		this.dataFim = dataFim;
	}

	public String getDataInicioFormatada() {
		return sdf.format(new Date(dataInicio));
	}

	public String[] getDataInicioSeparada() {
		return sdf.format(new Date(dataInicio)).split(" ");
	}

	public String getDataFimFormatada() {
		return sdf.format(new Date(dataFim));
	}

	public Long getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Long dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataInicioCompleta() {
		return new Date(dataInicio);
	}

	public Coleta getCicloAtividade() {
		return coleta;
	}

	public Coleta getColeta() {
		return coleta;
	}

	public void setColeta(Coleta cicloPai) {
		this.coleta = cicloPai;
	}

	public Calendar getDataManager() {
		return dataManager;
	}

	public void setDataManager(Calendar dataManager) {
		this.dataManager = dataManager;
	}

	public Long getTempoDecorrido() {
		return (dataFim - dataInicio);
	}

	public Integer getQuantidadeColetada() {
		return quantidadeColetada != 0 ? quantidadeColetada : 0;
	}

	public void setQuantidadeColetada(Integer quantidadeColetada) {
		if (atividade == null)
			return;
		if (this.atividade.getEhQuantitativa()) {
			this.quantidadeColetada = quantidadeColetada;
		}

	}

	public String tempoDecorridoCompleto() {
		String inicio = sdf.format(new Date(dataInicio));
		String fim = sdf.format(new Date(dataFim));
		return inicio + " - " + fim + " : " + getTempoDecorridoFormatado(false);
	}

	public String getTempoDecorridoFormatado(boolean pulaLinha) {
		StringBuilder formatado = new StringBuilder();
		formatado.append(FormatadorTempoDecorrido
				.formatarTempo(tempoCronometrado));
		String linha = pulaLinha ? "\n" : " - ";
		formatado.append(atividade.getEhQuantitativa() ? (linha
				+ atividade.getNomeItem() + ": " + getQuantidadeColetada())
				: "");
		return formatado.toString();
	}

	public String getTempoDecorridoFormatadoSimples() {
		StringBuilder formatado = new StringBuilder();
		formatado.append(FormatadorTempoDecorrido
				.formatarTempo(tempoCronometrado));
		return formatado.toString();
	}

	@Override
	public String toString() {
		return atividade.getTitulo() + " - "
				+ getTempoDecorridoFormatado(false);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(atividade).append(coleta)
				.append(dataFim).append(dataInicio).append(tempoCronometrado)
				.build();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TempoAtividade other = (TempoAtividade) obj;
		return new EqualsBuilder().append(atividade, other.atividade)
				.append(coleta, other.coleta).append(dataFim, other.dataFim)
				.append(dataInicio, other.dataInicio)
				.append(tempoCronometrado, other.tempoCronometrado).isEquals();
	}

	public void resetarDatas() {
		setDataFim(0l);
		setDataInicio(0l);
	}

	public String getQuantidadeColetadaFormatada() {
		return atividade.getNomeItem() + ": " + getQuantidadeColetada();
	}

	public long getTempoCronometradoEmSegundos() {
		long t = tempoCronometrado / 1000;
		return t;
	}

}
