import {useEffect, useState} from "react";
import axios from 'axios';
import GameBoard from "./component/GameBoard";


export default function Game() {

    const [game, setGame] = useState({
        pits: false,
        currentPlayer: undefined,
        gameStatus: undefined
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
        if (game.pits) {
            if(game.gameStatus === 'COMPLETED') {
                const message  = () => {
                    if(game.pits[aHousePit] === game.pits[bHousePit]) {
                        return "It was a draw game between player A and player B"
                    }
                    const winner  = game.pits[aHousePit] > game.pits[bHousePit] ? 'A' : 'B'
                    return "Winner is Player " + winner
                }
                window.alert(message());
                window.localStorage.removeItem("MY_GAME_MANCALA")
                setGame({});
                return
            }
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
            <div className={'linear-repeating border'}>
                <h3>Welcome to Mancala Kalaha</h3>
                <h4>A Strategic Board Game of Count and Capture.</h4>
                <button className='button' onClick={getGame} hidden={game.pits}>Start New Game</button>
                <button className="button"
                        hidden={!(game.pits) || game.gameStatus !== 'STARTED' }
                        onClick={() => { if (window.confirm('Do you really want to end this game ?')) {endGame(game.id) } }
                }>End Game</button>
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


