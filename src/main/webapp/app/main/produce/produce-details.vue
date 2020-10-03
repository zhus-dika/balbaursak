<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="produce">
                <form name="addToBucketForm" role="form" novalidate v-on:submit.prevent="addToBucket()" >
                    <h2 class="jh-entity-heading"><span v-text="$t('balbaursakApp.produce.detail.title')">Produce</span></h2>
                    <dl class="row jh-entity-details">
                        <dt>
                            <span v-text="$t('balbaursakApp.produce.name')">Name</span>
                        </dt>
                        <dd>
                            <span>{{produce.name}}</span>
                        </dd>
                        <dt>
                            <span v-text="$t('balbaursakApp.produce.unit')">Unit</span>
                        </dt>
                        <dd>
                            <span>{{produce.unit}}</span>
                        </dd>
                        <dt>
                            <span v-text="$t('balbaursakApp.produce.contains')">Contains</span>
                        </dt>
                        <dd>
                            <span>{{produce.contains}}</span>
                        </dd>
                        <dt>
                            <span v-text="$t('balbaursakApp.produce.days')">Days</span>
                        </dt>
                        <dd>
                            <span>{{produce.days}}</span>
                        </dd>
                        <dt>
                            <span v-text="$t('balbaursakApp.produce.description')">Description</span>
                        </dt>
                        <dd>
                            <span>{{produce.description}}</span>
                        </dd>
                        <dt>
                            <span v-text="$t('balbaursakApp.produce.price')">Price</span>
                        </dt>
                        <dd>
                            <span>{{produce.price}}</span>
                        </dd>
                        <dt>
                            <span v-text="$t('balbaursakApp.produce.file')">File</span>
                        </dt>
                        <dd>
                            <div v-if="produce.file">
                                <a v-on:click="openFile(produce.fileContentType, produce.file)">
                                    <img v-bind:src="'data:' + produce.fileContentType + ';base64,' + produce.file" style="max-width: 100%;" alt="produce image"/>
                                </a>
                            </div>
                        </dd>
                        <dt>
                            <span v-text="$t('balbaursakApp.produce.category')">Category</span>
                        </dt>
                        <dd>
                            <div v-if="produce.category">
                                {{produce.category.name}}
                            </div>
                        </dd>
                        <dt>
                            <span v-text="$t('balbaursakApp.requestpoint.quantity')">Quantity</span>
                        </dt>
                        <dd>
                            <div class="form-group">
                                <input type="number" class="form-control" name="unit" id="produce-unit"
                                       :class="{'valid': quantity, 'invalid': !quantity }" v-model="quantity" required/>
                                <div v-if="!quantity">
                                    <small class="form-text text-danger" v-if="!quantity" v-text="$t('entity.validation.required')">
                                        This field is required.
                                    </small>
                                </div>
                            </div>
                        </dd>
                    </dl>
                    <button type="submit"
                            v-on:click.prevent="previousState()"
                            class="btn btn-info">
                        <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                    </button>
                    <button v-if="produce.id" tag="button" class="btn btn-primary">
                        <span> Добавить в корзину</span>
                    </button>
                    <router-link :to="{name: 'ProduceFeedbacks', params: {produceId: produce.id}}" tag="button" class="btn btn-info details">
                        <font-awesome-icon icon="eye"></font-awesome-icon>
                        <span class="d-none d-md-inline" v-text="$t('balbaursakApp.feedback.home.title')">Feedbacks</span>
                    </router-link>
                </form>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./produce-details.component.ts">
</script>










