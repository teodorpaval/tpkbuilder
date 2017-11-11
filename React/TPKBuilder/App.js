import React, { Component } from 'react';
import { StyleSheet, Text, View, Button, ListView, TouchableHighlight, Alert, TextInput, Linking } from 'react-native';
import { StackNavigator } from 'react-navigation';


//Row data (hard-coded)
const rows = [
	{id: 0, text: 'Goblin', cr: 1},
	{id: 1, text: 'Hobgoblin', cr: 1},
	{id: 2, text: 'Black Dragon', cr:10},
	{id: 3, text: 'Lich', cr:12},
	{id: 4, text: 'Tarrasque', cr:20}
]

//wat is dis UPDATE: I KNOW WHAt THESE ARE NOW
//function that checks whether shit changed
const rowHasChanged = (r1, r2) => r1!==r2
//wat is dis also
//this creates a new DataSource with the checking function from above
const ds = new ListView.DataSource({rowHasChanged})


class MonsterList extends React.Component {
	
	constructor(props){
		super(props);
		//var ds = new ListView.DataSource({rowHasChanged});
		this.state = {
			dataSource: ds.cloneWithRows(rows),	
		}
		//this.state.dataSource.cloneWithRows(rows);
	}

	static navigationOptions = {
		title: 'Browse Screen',
	}
/*
	state = {
		dataSource: ds.cloneWithRows(rows),
		dataArray: rows,
	}
*/
	_onPressRow(rowData){
		const { navigate } = this.props.navigation;
		//rowData.cr = rowData.cr - 1
		//console.log(rowData)
		navigate('Edit', {'row': rowData})
		
		var newDs = [];
    	newDs = rows.slice();
   		this.setState({
      		dataSource: ds.cloneWithRows(newDs)
    	});
    	console.log(rowData)
	}

	componentDidMount(){
		var newDs = [];
    	newDs = rows.slice();
   		this.setState({
      		dataSource: ds.cloneWithRows(newDs)
    	});
	}

	renderRow = (rowData) => {
		return (
			<TouchableHighlight onPress={() => this._onPressRow(rowData)}>
				<Text style={styles.row}>
				{rowData.text} CR:{rowData.cr}
				</Text>
			</TouchableHighlight>
			)
	}

  render() {
  	return (
  		<ListView style={styles.container}
  			dataSource={this.state.dataSource}
  			renderRow={this.renderRow}
  		/>
  	)
  }
}



class EditScreen extends React.Component{
	static navigationOptions ={
		title: 'Edit Screen',
	}
	constructor(props){
		super(props);
		const rowData = this.props.navigation.state.params.row;
		this.state ={ text: rowData.text, cr: rowData.cr }
		
		//this.state.text = rowData.text
	}

	_onPress(rowData){
		//const { navigate } = this.props.navigation;
		var newDs = [];
    	newDs = rows.slice();
    	newDs[rowData.id].text = this.state.text;
    	newDs[rowData.id].cr = this.state.cr;
    	//this.props.navigation.goBack('Browse')
	}

	_onPressShare(){
		Linking.openURL('mailto:teopaval@gmail.com?subject=abcdefg&body=' + this.state.text)
	}

	render(){

		const rowData = this.props.navigation.state.params.row;
		//this.state.text = rowData.text
    	//console.log(newDs[rowData.id])
   		/*
   		this.setState({
      		dataSource: ds.cloneWithRows(newDs)
    	});*/
		return(
			<View style={styles.container}>
				<Text>this is the edit screen</Text>
				<TextInput
        			style={{height: 40, borderColor: 'gray', borderWidth: 1}}
        			value={this.state.text}
        			onChangeText={(text) => this.setState({text})}
        			
      			/>
      			<TextInput
        			style={{height: 40, borderColor: 'gray', borderWidth: 1}}
        			value={this.state.cr}
        			onChangeText={(text) => this.setState({text})}
        			
      			/>
				<Text>CR: {rowData.cr}</Text>
				<Button onPress = {() => this._onPress(rowData)}
					title="Save"
				/>
				<Button onPress = {() => this._onPressShare()}
					title="share"
				/>
			</View>
		);
	}
}

class BrowseScreen extends React.Component{
	static navigationOptions = {
		title: 'Browse Screen',
	}
	render() {
		return (
			<View style={styles.container}>
				<Text>Monster List</Text>
				<MonsterList/>
			</View>
		);
	}
}

export const RootNavigator = StackNavigator({
	Browse: { screen: MonsterList },
	Edit: { screen: EditScreen },
});
/*
export default class App extends React.Component{

	render(){
		return(
			<View style={styles.container}>
				<BrowseScreen/>
			</View>
		)
	}
}
*/
export default RootNavigator;
const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingTop: 22
  },
  row: {
  	padding: 15,
  	marginBottom: 5,
  	backgroundColor: 'skyblue',
  },
});
