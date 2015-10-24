package com.seimos.dgestao.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Moesio Medeiros
 * @date Fri Oct 24 13:01:42 BRT 2014
 */
@Entity
@Table
public class Fornecedor {
	@Id
	@GeneratedValue
	private Integer id;
	@Column(nullable = false, length = 250)
	private String nome;
	@Embedded
	private Endereco endereco;
	@Column(nullable = false, length = 50)
	private String contato;
	@Column(nullable = false, length = 16)
	private String cnpjCpf;
	@Column(nullable = false, length = 3)
	private Integer diasParaEntrega;
	@Column(nullable = false, length = 100)
	private String email;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public Fornecedor setNome(String nome) {
		this.nome = nome;
		return this;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public Fornecedor setEndereco(Endereco endereco) {
		this.endereco = endereco;
		return this;
	}

	public String getContato() {
		return contato;
	}

	public Fornecedor setContato(String contato) {
		this.contato = contato;
		return this;
	}

	public String getCnpjCpf() {
		return cnpjCpf;
	}

	public Fornecedor setCnpjCpf(String cnpjCpf) {
		this.cnpjCpf = cnpjCpf;
		return this;
	}

	public Integer getDiasParaEntrega() {
		return diasParaEntrega;
	}

	public Fornecedor setDiasParaEntrega(Integer diasParaEntrega) {
		this.diasParaEntrega = diasParaEntrega;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public Fornecedor setEmail(String email) {
		this.email = email;
		return this;
	}

}