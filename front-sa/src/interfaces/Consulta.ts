export interface Consulta {
  id: number;

  medicoNome: string;

  pacienteNome: string;
  pacienteCpf: string;
  pacienteDataNascimento: string;

  dataConsulta: string;
  horario: string;
  observacao: string;
}
