export interface MedicoResumo {
  idMedico: number;
  nome: string;
}

export interface PacienteResumo {
  idPaciente: number;
  nomePaciente: string;
  cpfPaciente: string;
}

export interface SintomaResumo {
  idSintoma: number;
  nomeSintoma: string;
}

export interface DoencaResumo {
  idDoenca: number;
  nomeDoenca: string;
}

export interface DoencaPrevista {
  id: number;
  nome: string;
  compatibilidade: number;
}

export interface Consulta {
  idConsulta: number;
  medico: MedicoResumo;
  paciente: PacienteResumo;
  dataConsulta: string;
  descricao: string;
  sintomas: SintomaResumo[];
  doencas: DoencaResumo[];
  previsoes: DoencaPrevista[];
}