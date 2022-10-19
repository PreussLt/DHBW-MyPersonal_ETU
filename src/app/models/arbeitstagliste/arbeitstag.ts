export class Arbeitstag {
  ersterStempel: string;
  letzterStempel: string;
  calendarWeek: number;
  calendarYear: number;
  mid: string;

  // Public Boolens
  tag: string;
  arbeitszeit: number;
  zeitstempel: string[][];

  feiertag: boolean;
  gleitzeittag: boolean;
  urlaubstag: boolean;
  arbeitszeitenEingehalten: boolean;
  pausenEingehalten: boolean;
  maxArbeitszeitEingehalten: boolean;
}
