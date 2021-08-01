package br.com.projetospring.dto;

import br.com.projetospring.entities.Categoria;

public class CategoriaDTO  {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;

    public CategoriaDTO(){

    }

    /*Para que na CategoriaController aceite e crie uma lista de DTO
    * podemos criar um contrutor do tipo da classe para que na classe em questao aceite
    * uma lista de dto ao invez de recebermos uma lista com todos os paramentros que englobam
    * a lista de categorias que sao os produtos assim formataremos para que a categoria quando
    * pesquisarmos todas so venham o nome da categoria e seu id
      */

    public CategoriaDTO(Categoria obj){
        id = obj.getId();
        nome = obj.getNome();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
