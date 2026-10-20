package repository;

import model.Prontuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProntuarioRepository {
    private static Long proximoID = 1L;
    private static Map<Long, Prontuario> prontuarios = new HashMap<>();

    public void salvarProntuario(Prontuario prontuario){
        if (prontuario.getId() == null){
            prontuario.setId(proximoID++);
        }
        prontuarios.put(prontuario.getId(), prontuario);
    }

    public Prontuario buscarPorId(Long id){
        Prontuario encontrado = prontuarios.get(id);
        return encontrado;
    }

    public List<Prontuario> listarProntuarios(){
        return new ArrayList<>(prontuarios.values());
    }

    public List<Prontuario> listarPorPaciente(Long idPaciente){
        List<Prontuario> resultados = new ArrayList<>();

        for (Prontuario p :  listarProntuarios()) {
            if (idPaciente.equals(p.getConsultas().getPaciente().getId())) {
                resultados.add(p);
            }
        }
        return resultados;
    }

    public List<Prontuario> listarPorDentista(Long idDentista){
        List<Prontuario> resultados = new ArrayList<>();

        for (Prontuario p :  listarProntuarios()) {
            if (idDentista.equals(p.getConsultas().getDentista().getId())) {
                resultados.add(p);
            }
        }
        return resultados;
    }
}
