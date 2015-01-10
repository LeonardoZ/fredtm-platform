package com.fredtm.core.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Coleta extends Entidade {

	private static final long serialVersionUID = 4085712607350133267L;
	private Comparator<TempoAtividade> comparator = (lhs, rhs) -> rhs
			.getDataInicio().compareTo(lhs.getDataInicio());

	private Comparator<TempoAtividade> comparatorInverso = (lhs, rhs) -> lhs
			.getDataInicio().compareTo(rhs.getDataInicio());

	private Comparator<Atividade> comparatorAtividades = (lhs, rhs) -> {
		if (rhs.getTipoAtividade().equals(lhs.getTipoAtividade())) {
			return lhs.getTitulo().compareTo(rhs.getTitulo());
		}
		return rhs.getTipoAtividade().compareTo(lhs.getTipoAtividade());
	};

	private Operacao operacao;
	private HashMap<Long, List<TempoAtividade>> temposColetados;
	private List<Atividade> atividades;

	public Coleta() {
		atividades = new ArrayList<Atividade>();
		temposColetados = new HashMap<Long, List<TempoAtividade>>();
	}

	public Coleta(Coleta coleta) {
		this();
		this.setId(coleta.getId());
		this.setOperacao(coleta.getOperacao());
	}

	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

	public Atividade getAtividadeQuantitativa() {
		return operacao.getAtividadeQuantitativa();
	}

	public TempoAtividade addNovoTempo(Atividade atividade, Long inicio,
			Long fim, Long tempoCronometrado) {
		TempoAtividade tempoAtividade = new TempoAtividade(atividade, this);
		tempoAtividade.setDataInicio(inicio);
		tempoAtividade.setDataFim(fim);
		tempoAtividade.setTempoCronometrado(tempoCronometrado);
		List<TempoAtividade> tempoAtividades = temposColetados.get(atividade
				.getId());
		tempoAtividades.add(tempoAtividade);
		organizarTempos(tempoAtividades);
		return tempoAtividade;
	}

	private void organizarTempos(List<TempoAtividade> tempos) {
		Collections.sort(tempos, comparator);
	}

	private void organizarAtividades() {
		Collections.sort(atividades, comparatorAtividades);
	}

	public TempoAtividade alterarFinal(Atividade atividade, Long inicio,
			Long novoFim, Long tempoCronometrado) {
		for (TempoAtividade ta : temposColetados.get(atividade.getId())) {
			if (ta.getDataInicio().equals(inicio)) {
				ta.setDataFim(novoFim);
				ta.setTempoCronometrado(tempoCronometrado);
				return ta;
			}
		}
		return null;
	}

	public List<TempoAtividade> getTemposColetados(Atividade param) {
		return temposColetados.get(param.getId());
	}

	public Operacao gerarOperacao(int i) {
		Operacao operacao = new Operacao("Teste " + i, "Teste empresa " + i,
				"Primeira operação " + i);
		for (int j = 0; j < 20; j++) {
			TipoAtividade t = i % 2 == 0 ? TipoAtividade.PRODUTIVA
					: j % 2 == 1 && j != 5 ? TipoAtividade.IMPRODUTIVA
							: TipoAtividade.PRODUTIVA;
			Atividade atividade = new Atividade("Atv " + j,
					"Essa é a atv " + j, t);
			atividade.setId(i+j*new Date().getTime());
			atividade.setOperacao(operacao);
			operacao.addAtividade(atividade);
		}
		List<Atividade> atividadesPreDefinidas = operacao
				.getAtividadesPreDefinidas();
		for (int g = 0; g < 10; g++) {
			Coleta coleta = new Coleta();
			coleta.setOperacao(operacao);
			coleta.setAtividades(atividadesPreDefinidas);
			atividadesPreDefinidas.forEach(a -> {
				for (int z = 0; z < 5; z++) {
					coleta.addNovoTempo(a, new Date().getTime() + 1500,
							new Date().getTime() + 3000, (long) z * 1000);
				}
			});
			operacao.addColeta(coleta);
		}

		return operacao;

	}

	public List<Long> listaDeSomaDosTempos() {
				return temposColetados
					.values().stream()
					.peek(z -> z.forEach(z1-> System.err.println(z1)))
					.map(m -> 
						m.stream().map((TempoAtividade ml) -> ml.getTempoCronometradoEmSegundos())
					 )
					 .map(s -> s.collect(Collectors.toList()))
					 .mapToLong(g -> g.stream().reduce(Long::sum).get())
					 .boxed()
					 .collect(Collectors.toList());

	}
 
	public static void main(String[] args) {
		new Coleta().gerarOperacao(1).getColetas().get(0).listaDeSomaDosTempos();
	}

	public List<TempoAtividade> getTempos() {
		Collection<List<TempoAtividade>> values = temposColetados.values();
		List<TempoAtividade> todos = new ArrayList<TempoAtividade>();
		for (List<TempoAtividade> lt : values) {
			for (TempoAtividade t : lt) {
				todos.add(t);
			}
		}
		return todos;
	}

	public void remove(TempoAtividade tempo) {
		Collection<List<TempoAtividade>> values = temposColetados.values();
		for (List<TempoAtividade> lt : values) {
			if (lt.contains(tempo)) {
				lt.remove(tempo);
				return;
			}
		}
	};

	public List<TempoAtividade> getTemposEmOrdemCronologica() {
		List<TempoAtividade> tempos = getTempos();
		Collections.sort(tempos, comparatorInverso);
		return tempos;
	}

	public String getPrimeiraDataFormatada() throws NoSuchElementException {
		Date date = getPrimeiroTempo();
		return new SimpleDateFormat("dd/MM/yyyy").format(date);
	}

	public String getPrimeiraHoraFormatada() {
		Date date = getPrimeiroTempo();
		return new SimpleDateFormat("hh:mm:ss").format(date);
	}

	private Date getPrimeiroTempo() {
		List<TempoAtividade> tempos = getTemposEmOrdemCronologica();
		if (tempos.size() == 0) {
			throw new NoSuchElementException();
		}
		return tempos.get(0).getDataInicioCompleta();
	}

	public void addAtividade(Atividade atv, List<TempoAtividade> tempos) {
		this.atividades.add(atv);
		this.temposColetados.put(atv.getId(), tempos);
		organizarTempos(tempos);
	}

	@Override
	public String toString() {
		return operacao.getNome() + " - " + getPrimeiraDataFormatada();
	}

	public List<Atividade> listarAtividades() {
		organizarAtividades();
		return atividades;
	}

	public List<Atividade> getAtividades() {
		Collections.sort(atividades, comparatorAtividades);
		return atividades;
	}

	public void setAtividades(List<Atividade> atividades) {
		for (Atividade a : atividades) {
			addNovaAtividade(a);
		}
	}

	public void addNovaAtividade(Atividade atividade) {
		atividades.add(atividade);
		temposColetados.put(atividade.getId(), new ArrayList<TempoAtividade>());
	}

	public void remover(Atividade at) {
		atividades.remove(at);
		temposColetados.remove(at.getId());
	}

	public String getTudo() {
		return temposColetados.toString();
	}

}
