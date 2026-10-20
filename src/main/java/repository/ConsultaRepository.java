package repository;

import model.Consulta;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsultaRepository {

    private static Long proximoID = 1L;
    private static Map<Long, Consulta> consultas = new HashMap<>();

    public void salvarConsulta(Consulta consulta){
        if(consulta.getId() == null){
            consulta.setId(proximoID++);
        }
        consultas.put(consulta.getId(), consulta);
    }

    public Consulta buscarPorId(Long id){
        Consulta encontrado = consultas.get(id);
        return encontrado;
    }

    public List<Consulta> listarConsultas(){
        return new ArrayList<>(consultas.values());
    }

    public List<Consulta> listarPorPaciente(Long idPaciente){
        List<Consulta> resultados = new ArrayList<>();

        for (Consulta c :  listarConsultas()) {
            if (idPaciente.equals(c.getPaciente().getId())) {
                resultados.add(c);
            }
        }
        return resultados;
    }

    public List<Consulta> listarPorDentista(Long idDentista){
        List<Consulta> resultados = new ArrayList<>();

        for (Consulta c :  listarConsultas()) {
            if (idDentista.equals(c.getDentista().getId())) {
                resultados.add(c);
            }
        }
        return resultados;
    }

    public List<Consulta> listarPorStatus(Consulta.Status status){
        List<Consulta> resultados = new ArrayList<>();

        for (Consulta c :  listarConsultas()) {
            if (status == c.getStatusAtual()) {
                resultados.add(c);
            }
        }
        return resultados;
    }
}
