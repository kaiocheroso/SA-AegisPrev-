import type { Sintoma } from "./Sintoma";

export interface Doenca {
  idDoenca: number;
  nomeDoenca: string;
  descricaoDoenca: string;
  hereditaria: boolean;
  sintomas: SintomaResumo[];
}

export interface SintomaResumo {
  idSintoma: number;
  nomeSintoma: string;
}