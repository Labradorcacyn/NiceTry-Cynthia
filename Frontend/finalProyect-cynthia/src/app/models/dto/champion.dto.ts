export class ChampionDto {
  name: string;
  cost: number;
  description: string;
  traitsUuid: number[];
  constructor() {
      this.name = '';
      this.cost = 0;
      this.description = '';
      this.traitsUuid = [];
  }
}
