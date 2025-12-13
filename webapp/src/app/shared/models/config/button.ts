export class Button {

  constructor(
    public title?: string,
    public icon?: string,
    public background?: string,
    public fontColor?: string,
    public type?: string,
    public isActive: boolean = true,
    public action?: () => void,
  ) {
  }
}
