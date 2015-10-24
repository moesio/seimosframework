package com.seimos.dgestao.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * @author Moesio Medeiros
 * @date Fri Oct 24 13:02:53 BRT 2014
 */
@Embeddable
public class Endereco
{
	@Column(nullable = true, length = 100)
	private String logradouro;
	@Column(nullable = true, length = 10)
	private String numero;
	@Column(nullable = true, length = 100)
	private String complemento;
	@Column(nullable = true, length = 8)
	private String cep;
	@Column(nullable = true, length = 100)
	private String bairro;
	@Column(nullable = true, length = 100)
	private String cidade;
	@Column(nullable = true, length = 2)
	private String uf;
	
	public String getLogradouro() {
		return logradouro;
	}
	public Endereco setLogradouro(String logradouro) {
		this.logradouro = logradouro;
		return this;
	}
	public String getNumero() {
		return numero;
	}
	public Endereco setNumero(String numero) {
		this.numero = numero;
		return this;
	}
	public String getComplemento() {
		return complemento;
	}
	public Endereco setComplemento(String complemento) {
		this.complemento = complemento;
		return this;
	}
	public String getCep() {
		return cep;
	}
	public Endereco setCep(String cep) {
		this.cep = cep;
		return this;
	}
	public String getBairro() {
		return bairro;
	}
	public Endereco setBairro(String bairro) {
		this.bairro = bairro;
		return this;
	}
	public String getCidade() {
		return cidade;
	}
	public Endereco setCidade(String cidade) {
		this.cidade = cidade;
		return this;
	}
	public String getUf() {
		return uf;
	}
	public Endereco setUf(String uf) {
		this.uf = uf;
		return this;
	}
	
}