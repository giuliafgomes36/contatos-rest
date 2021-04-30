package com.exemple.contatos.rest;

import com.exemple.contatos.model.Contato;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

//A anotação @Path indica qual o caminho HTTP a classe irá responder, como não existem outras classes de serviço, ela irá tratar todos os caminhos ("/").
@Path("/")
public class ContatoController {
    //Lista de contatos:
    private static List<Contato> contatos = new ArrayList<>();

    /* Método para recuperar um contato em específico a partir do seu ID:
     * @GET: será invocado em requisições do tipo GET;
     * @Produces: formato de mídia que sera produzido (JSON);
     * @Path: caminho HTTP que o método irá responder, juntamente com a URL do serviço, será passado um ID
     * @PathParam: o ID na URL será usado como o atributo passado dentro do método
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{idContato}")
    public Contato getContato(@PathParam("idContato") Integer id) {
        Contato resultado = null;

        for(Contato contato: contatos) {
            if(contato.getId() == id) {
                resultado = contato;
                break;
            }
        }

        if(resultado==null) throw new WebApplicationException(404);

        return resultado;
    }


    /* Método para retornar uma lista com os contatos:
     * @GET: será invocado em requisições do tipo GET;
     * @Produces: formato de mídia produzido (JSON);
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Contato> getContatos() {
        return contatos;
    }

    /* Método para buscar contatos a partir de um nome:
     * @GET: será invocado em requisições do tipo GET;
     * @Produces: tipo de mídia produzido (JSON)
     * @Path: caminho HTTP
     * @PathParam: nome presente na URL sera passado como parâmetro
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/find/{nome}")
    public List<Contato> getContatoPorNome(@PathParam("nome") String nome) {
        List<Contato> resultado = new ArrayList<>();

        for(Contato contato: contatos) {
            if(contato.getNome().toLowerCase().equals(nome.toLowerCase()) && contato.getNome() != null) {
                resultado.add(contato);
            }
        }

        return resultado;
    }

    /*
     * Método para adicionar um contato:
     * @POST: será invocado em requisições do tipo POST
     * @Consumes: tipo de dado que irá receber do cliente (JSON)
     * @Produces: tipo de dado devolvido ao cliente (JSON)
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Contato adicionarContato(Contato contato) {

        if(contato.getNome() == null || contato.getNome().trim().equals("")) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("O nome do contato é obrigatório").build());

        }

        contatos.add(contato);
        contato.setId(contatos.indexOf(contato));

        return contato;
    }

    /* Método para atualizar um contato:
     * @PUT: será invocado em requisições do tipo PUT
     * @Path: caminho que irá invocar o método
     * @Consumes: tipo de dado que ira receber do cliente
     * @PathParam: id presente na URL será passada como parâmetro
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void atualizarContato(@PathParam("id") Integer id, Contato contato) {
        contatos.set(id, contato);
        contato.setId(contatos.indexOf(contato));
    }

    /* Método para deletar um contato:
     * @DELETE: invocado em requisições do tipo DELETE
     * @Path: caminho que irá chamar o método
     */
    @DELETE
    @Path("/{id}")
    public void deletarContato(@PathParam("id") Integer id) {
        Contato contato = contatos.get(id);
        contatos.remove(contato);
    }
}
