import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IProduce } from '@/shared/model/produce.model';
import ProduceService from './produce.service';

@Component
export default class ProduceDetails extends mixins(JhiDataUtils) {
  @Inject('produceService') private produceService: () => ProduceService;
  public produce: IProduce = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.produceId) {
        vm.retrieveProduce(to.params.produceId);
      }
    });
  }

  public retrieveProduce(produceId) {
    this.produceService()
      .find(produceId)
      .then(res => {
        this.produce = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
