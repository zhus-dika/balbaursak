import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IProduce } from '@/shared/model/produce.model';
import ProduceService from './produce.service';
import AlertService from '@/shared/alert/alert.service';
import { IRequestpoint } from '@/shared/model/requestpoint.model';

@Component
export default class ProduceDetails extends mixins(JhiDataUtils) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('produceService') private produceService: () => ProduceService;
  public produce: IProduce = {};
  public requestpoints: IRequestpoint[] = [];
  public isSaving = false;
  public quantity = 1;
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.produceId) {
        vm.retrieveProduce(to.params.produceId);
      }
    });
  }
  public addToBucket() {
    this.isSaving = true;
    localStorage.setItem(`requestPoint_${this.produce.id}`, `${this.quantity}`);
    const item = {
      key: `requestPoint_${this.produce.id}`,
      val: this.quantity
    };
    this.$store.commit('additem', item);
    this.$router.go(-1);
    const message = `Изделие ${this.produce.id} добавлено в корзину`;
    this.alertService().showAlert(message, 'info');
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
