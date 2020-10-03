import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRequestpoint } from '@/shared/model/requestpoint.model';
import AlertMixin from '@/shared/alert/alert.mixin';
import ProduceService from '@/main/produce/produce.service';
import { IProduce } from '@/shared/model/produce.model';
import { IRequest } from '@/shared/model/request.model';


@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Requestpoint extends mixins(AlertMixin) {
  @Inject('produceService') private produceService: () => ProduceService;
  public produce: IProduce = {};
  private removeId: number = null;
  public reverse = false;
  public propOrder = 'id';
  public totalItems = 0;
  public requestpoints: IRequestpoint[] = [];
  public request: IRequest = {};

  public mounted(): void {
    this.retrieveAllRequestpoints();
  }

  public retrieveAllRequestpoints(): void {
    const bucket = this.$store.getters.bucket;
    bucket.forEach((ele, idx) => {
      const produceId = parseInt(ele.key.substr(13), 10);
      this.produceService()
        .find(produceId)
        .then(res => {
          this.produce = res;
      const requestPoint = {
        id: idx + 1,
        quantity: parseInt(ele.val, 10),
        total: this.produce.price * parseInt(ele.val, 10),
        produce: this.produce
      };
      this.requestpoints.push(requestPoint);
        });
    });
  }
  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
  public retrieveProduce(produceId) {
    this.produceService()
      .find(produceId)
      .then(res => {
        this.produce = res;
      });
  }
  public prepareRemove(instance: IRequestpoint): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }
  public removeRequestpoint(): void {
    const requestpoint = this.requestpoints.filter(ele => ele.id === this.removeId)[0];
    this.requestpoints = this.requestpoints.filter(ele => ele.id !== this.removeId);
    const key = `requestPoint_${requestpoint.produce.id}`;
    localStorage.removeItem(key);
    this.$store.commit('removeitem', key);
    this.closeDialog();
  }
  public previousState() {
    this.$router.go(-1);
  }
  sendRequest() {

  }
}
