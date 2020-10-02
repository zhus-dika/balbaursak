<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="produce">
                <h2 class="jh-entity-heading"><span v-text="$t('balbaursakApp.produce.detail.title')">Produce</span> {{produce.id}}</h2>
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
                            {{produce.fileContentType}}, {{byteSize(produce.file)}}
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('balbaursakApp.produce.category')">Category</span>
                    </dt>
                    <dd>
                        <div v-if="produce.category">
                            <router-link :to="{name: 'CategoryView', params: {categoryId: produce.category.id}}">{{produce.category.name}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="produce.id" :to="{name: 'ProduceEdit', params: {produceId: produce.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./produce-details.component.ts">
</script>
