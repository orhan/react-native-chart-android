/* @flow */
'use strict';

var React = require('react');

import {
  StyleSheet,
  View,
  TouchableOpacity,
  Text
} from 'react-native';

var Button = React.createClass({
	onPress:function (argument) {
		this.props.onPress&&this.props.onPress();
	},
	render: function() {
		return (
			<TouchableOpacity activeOpacity ={0.5} underlayColor="#B5B5B5" onPress={this.props.onPress}>
		        <View  style={[styles.button,this.props.style]}>
		        	<Text>{this.props.text}</Text>
		        </View>
      		</TouchableOpacity >
		);
	}
});


var styles = StyleSheet.create({
	button:{
		height:50,
		justifyContent:'center',
		alignItems: 'center',
		borderBottomWidth:1,
		borderBottomColor:'#e5e5e5'
	}
});


module.exports = Button;
