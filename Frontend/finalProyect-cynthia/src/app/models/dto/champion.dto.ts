export class ChampionDto {
  name: string;
  cost: number;
  traitsUuid: number[];
  constructor() {
      this.name = '';
      this.cost = 0;
      this.traitsUuid = [];
  }
}
