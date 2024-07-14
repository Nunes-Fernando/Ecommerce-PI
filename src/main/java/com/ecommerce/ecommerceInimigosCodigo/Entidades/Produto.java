package com.ecommerce.ecommerceInimigosCodigo.Entidades;

import javax.persistence.*;

import java.util.Base64;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private double preco;
    private int quantidade;
    private String descricao;
    private int avaliacao;

    @Column(name = "imagem_path", columnDefinition = "MEDIUMBLOB")
    private byte[] imagemPath;

    // Construtor, getters e setters...


    public Produto(Long id, String nome, double preco, int quantidade, String descricao, int avaliacao, byte[] imagemPath) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.avaliacao = avaliacao;
        this.imagemPath = imagemPath;
    }

    public Produto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
    }

    public byte[] getImagemPath() {
        return imagemPath;
    }

    public void setImagemPath(byte[] imagemPath) {
        this.imagemPath = imagemPath;
    }

    public String getImagemPathAsBase64() {
        if (imagemPath != null) {
            return Base64.getEncoder().encodeToString(imagemPath);
        }
        return null;
    }

    public void setImagemPathAsBase64(String base64String) {
        if (base64String != null && !base64String.isEmpty()) {
            this.imagemPath = Base64.getDecoder().decode(base64String);
        } else {
            this.imagemPath = null;
        }
    }
}
