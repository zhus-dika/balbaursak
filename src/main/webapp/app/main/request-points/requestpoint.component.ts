import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRequestpoint } from '@/shared/model/requestpoint.model';
import AlertMixin from '@/shared/alert/alert.mixin';
import ProduceService from '@/main/produce/produce.service';
import { IProduce } from '@/shared/model/produce.model';

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
      console.log(parseInt(ele.val, 10));
      console.log(this.produce);
      const requestPoint = {
        id: idx,
        quantity: parseInt(ele.val, 10),
        total: this.produce.price * parseInt(ele.val, 10),
        produce: this.produce
      };
      this.requestpoints.push(requestPoint);
        });
    });
  }
  public retrieveProduce(produceId) {
    this.produceService()
      .find(produceId)
      .then(res => {
        this.produce = res;
      });
  }
}
