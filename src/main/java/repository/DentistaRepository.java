package repository;

import model.Dentista;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DentistaRepository {

    private static Long proximoID = 1L;
    private static Map<Long, Dentista> dentistas = new HashMap<>();

    public void salvarDentista(Dentista dentista){
        if(dentista.getId() == null){
            dentista.setId(proximoID++);
        }
        dentistas.put(dentista.getId(), dentista);
    }

    public Dentista buscarPorId(Long id){
        Dentista encontrado = dentistas.get(id);
        return encontrado;
    }

    public List<Dentista> listarDentistas(){
        return new ArrayList<>(dentistas.values());
    }

    //Metodo auxiliar para limpar o texto antes de comparar
    private String normalizarTexto(String nome) {
        if (nome == null) return "";
        return Normalizer.normalize(nome, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(); // Garante que maiúsculas/minúsculas não quebrem a busca
    }

    public List<Dentista> buscarPorNome(String nome){
        List<Dentista> resultados = new ArrayList<>();
        String nomeBuscado = normalizarTexto(nome);

        for(Dentista d : listarDentistas()){
            String dentistaNome = normalizarTexto(d.getNome());
            if(dentistaNome.contains(nomeBuscado)){
                resultados.add(d);
            }
        }
        return resultados;
    }

    public  Dentista buscarPorCro(String cro) {
        for (Dentista dentista : listarDentistas()) {
            if (cro.equals(dentista.getCro())) {
                return dentista;
            }
        }
        return null;
    }

    public  Dentista buscarPorEmail(String email) {
        for (Dentista dentista : listarDentistas()) {
            if (email.equals(dentista.getEmail())) {
                return dentista;
            }
        }
        return null;
    }
}
