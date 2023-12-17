import {useEffect, useState} from "react";
import axios from 'axios';
import GameBoard from "./component/GameBoard";


export default function Game() {

    const [game, setGame] = useState({
        pits: false,
        currentPlayer: undefined
    })

    const [aLastPit, setALastPit] = useState(0)
    const [bLastPit, setBLastPit] = useState(0)
    const [aHousePit, setAHousePit] = useState(0)
    const [bHousePit, setBHousePit] = useState(0)

    useEffect(() => {
        const data = window.localStorage.getItem("MY_GAME_MANCALA")
        if (data) {
            setGame(JSON.parse(data))
        }
    }, []);

    useEffect(() => {
        if(game.pits) {
            setAHousePit((game.pits.length / 2) - 1)
            setBHousePit(game.pits.length - 1)
            setALastPit(aHousePit - 1)
            setBLastPit(bHousePit - 1)
            window.localStorage.setItem("MY_GAME_MANCALA", JSON.stringify(game))
        }
    }, [game, aHousePit, bHousePit])

    const getGame = (e) => {
        e.preventDefault()
        axios.get('/start')
            .then(res => {
                setGame(res.data)
            })
            .catch(err => console.log(err))
    }

    const handleMove = (pitIndex) => {
        const currentMove = {'gameId': game.id, 'pitIndex': pitIndex, 'player': game.currentPlayer};

        axios.put('/move',
                JSON.stringify(currentMove),
        {headers: {'Content-Type': 'application/json;charset=UTF-8'}})
            .then(res => {
            setGame(res.data)
        }).catch(err => console.log(err))
    };

    const endGame = (gameId) => {
        axios.post('/end',
            {gameId: gameId}, {headers: {'Content-Type': 'application/x-www-form-urlencoded'}})
            .then(res => {
                setGame({})
                window.localStorage.removeItem("MY_GAME_MANCALA")
            }).catch(err => console.log(err))
    }

    return (
        <div className={'game'}>
            <div className={'message-banner'}>
                <h4>Welcome to Mancala Kalaha Game</h4>
                <button className='button' onClick={getGame} hidden={game.pits}>Start New Game</button>
                <button className='button' onClick={()=> endGame(game.id) } hidden={!game.pits}>End Game</button>
            </div>
            {game.pits ?
                <div>
                    <GameBoard
                        game={game}
                        aHousePit={aHousePit}
                        bHousePit={bHousePit}
                        aLastPit={aLastPit}
                        bLastPit={bLastPit}
                        handleMove={handleMove}
                    />
                </div>
                : ''}
        </div>
    )

}


