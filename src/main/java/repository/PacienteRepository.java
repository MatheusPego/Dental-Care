package repository;

import model.Paciente;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.Normalizer;

public class PacienteRepository {

    private static Long proximoID = 1L;
    private static Map<Long, Paciente> pacientes = new HashMap<>();

    public void salvarPaciente(Paciente paciente){
        if(paciente.getId() == null){
            paciente.setId(proximoID++);
        }
        pacientes.put(paciente.getId(), paciente);
    }

    public Paciente buscarPorId(Long id){
         Paciente encontrado = pacientes.get(id);
         return encontrado;
    }

    public List<Paciente> listarPacientes(){
        return new ArrayList<>(pacientes.values());
    }

    //Metodo auxiliar para limpar o texto antes de comparar
    private String normalizarTexto(String nome) {
        if (nome == null) return "";
        return Normalizer.normalize(nome, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "") //remove o que não for letra
                .toLowerCase(); // Garante que maiúsculas/minúsculas não quebrem a busca
    }

    public List<Paciente> buscarPorNome(String nome){
        List<Paciente> resultados = new ArrayList<>();
        String nomeBuscado = normalizarTexto(nome);

        for(Paciente p : listarPacientes()){
            String pacienteNome = normalizarTexto(p.getNome());
            if(pacienteNome.contains(nomeBuscado)){
                resultados.add(p);
            }
        }
        return resultados;
    }

    public  Paciente buscarPorCpf(String cpf) {
        for (Paciente paciente : listarPacientes()) {
            if (cpf.equals(paciente.getCpf())) {
                return paciente;
            }
        }
        return null;
    }
}
