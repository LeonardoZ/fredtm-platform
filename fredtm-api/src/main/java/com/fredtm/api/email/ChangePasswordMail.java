package com.fredtm.api.email;

public class ChangePasswordMail {
	public String getUrl2() {
		return "https://192.168.1.103:9000/fred/change/password?token=";
	}
	
	public String getTitle() {
		return "Fred TM - Troca de Senha";
	}

	public String getContent(String token) {
		return String.format("<!doctype html><html><body>"
				+ "Abra o link abaixo para alterar a sua senha:\n\n "
				+ "<a href=\"%s%s\">Link de Troca de Senha</a>"
				+ "<br />---<br />"
				+ "Se o link não estiver aparecendo no seu browser, copie e cole no seu navegador o link abaixo:"
				+ "<br />%s%s</body></html>", getUrl2(), token, getUrl2(),token);

	}

}
