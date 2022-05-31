export interface Trait {
  id: number;
  name: string;
  description: string;
  avatar: string;
}

export interface ChampionResponse {
  id: string;
  name: string;
  cost: number;
  traits: Trait[];
  avatar: string;
}
