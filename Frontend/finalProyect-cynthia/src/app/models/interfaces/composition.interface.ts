export interface Trait {
  id: number;
  name: string;
  description: string;
  avatar: string;
}

export interface Champion {
  id: string;
  name: string;
  cost: number;
  traits: Trait[];
  avatar: string;
}

export interface CompositionResponse {
  id: string;
  name: string;
  description: string;
  authorNick: string;
  authorAvatar: string;
  date: Date;
  champions: Champion[];
  votes: any[];
  comments: number;
}
