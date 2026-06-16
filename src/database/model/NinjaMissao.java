package database.model;

public class NinjaMissao {

    private Long id;
    private Long idNinja;
    private Long idMissao;
    private String nomeNinja;
    private String tituloMissao;
    private String funcao;
    private String dataParticipacao;

    public NinjaMissao() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdNinja() { return idNinja; }
    public void setIdNinja(Long idNinja) { this.idNinja = idNinja; }

    public Long getIdMissao() { return idMissao; }
    public void setIdMissao(Long idMissao) { this.idMissao = idMissao; }

    public String getNomeNinja() { return nomeNinja; }
    public void setNomeNinja(String nomeNinja) { this.nomeNinja = nomeNinja; }

    public String getTituloMissao() { return tituloMissao; }
    public void setTituloMissao(String tituloMissao) { this.tituloMissao = tituloMissao; }

    public String getFuncao() { return funcao; }
    public void setFuncao(String funcao) { this.funcao = funcao; }

    public String getDataParticipacao() { return dataParticipacao; }
    public void setDataParticipacao(String dataParticipacao) { this.dataParticipacao = dataParticipacao; }

}
