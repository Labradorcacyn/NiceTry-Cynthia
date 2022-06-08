export interface Trait {
  id: number;
  name: string;
  description: string;
}

export interface ChampionResponse {
  traitsUuid: Trait[];
  id: string;
  name: string;
  cost: number;
  description: string;
  traits: Trait[];
}


