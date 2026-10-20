package repository;

import model.Recepcionista;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecepcionistaRepository {

    private static Long proximoID = 1L;
    private static Map<Long, Recepcionista> recepcionistas = new HashMap<>();

    public void salvarRecepcionista(Recepcionista recepcionista){
        if(recepcionista.getId() == null){
            recepcionista.setId(proximoID++);
        }
        recepcionistas.put(recepcionista.getId(), recepcionista);
    }

    public Recepcionista buscarPorId(Long id){
        Recepcionista encontrado = recepcionistas.get(id);
        return encontrado;
    }

    public List<Recepcionista> listarRecepcionistas(){
        return new ArrayList<>(recepcionistas.values());
    }

    //Metodo auxiliar para limpar o texto antes de comparar
    private String normalizarTexto(String nome) {
        if (nome == null) return "";
        return Normalizer.normalize(nome, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(); // Garante que maiúsculas/minúsculas não quebrem a busca
    }

    public List<Recepcionista> buscarPorNome(String nome){
        List<Recepcionista> resultados = new ArrayList<>();
        String nomeBuscado = normalizarTexto(nome);

        for(Recepcionista r : listarRecepcionistas()){
            String recepcionistaNome = normalizarTexto(r.getNome());
            if(recepcionistaNome.contains(nomeBuscado)){
                resultados.add(r);
            }
        }
        return resultados;
    }

    public List<Recepcionista> buscarPorTurno(String turno){
        List<Recepcionista> resultados = new ArrayList<>();
        String turnoBuscado = normalizarTexto(turno);

        for(Recepcionista d : listarRecepcionistas()){
            String recepcionistaTurno = normalizarTexto(d.getTurno());

            if(recepcionistaTurno.contains(turnoBuscado)){
                resultados.add(d);
            }
        }
        return resultados;
    }

    public  Recepcionista buscarPorEmail(String email) {
        for (Recepcionista recepcionista : listarRecepcionistas()) {
            if (email.equals(recepcionista.getEmail())) {
                return recepcionista;
            }
        }
        return null;
    }
}
